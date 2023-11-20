import axios from 'axios';
import { NextResponse } from 'next/server';
import { useTokenStore } from '@/store/useLogin';
const response = NextResponse.next();

const app = axios.create({
  // baseURL: `${process.env.NEXT_PUBLIC_API_URL}/auth`,
  baseURL: `/auth`,
  withCredentials: true,
});

app.interceptors.request.use(
  (res) => {
    const accessToken = useTokenStore.getState().accessToken;
    if (accessToken) {
      res.headers['Authorization'] = `Bearer ${accessToken}`;
    }
    console.log('header', res);
    return res;
  },
  (err) => Promise.reject(err),
);

app.interceptors.response.use(
  async (res) => {
    /** access token **/
    await setAccessToken(res);
    return res.data;
  },
  async (err) => {
    const originalConfig = err.config;
    if (err.response.status === 401 && !originalConfig._retry) {
      originalConfig._retry = true;
      // 토큰 다시 가져오기
      /*try {
        const { data } = await axios.get(`/auth/reissue`, {
          withCredentials: true,
        });
        if (data) return app(originalConfig);
      } catch (error) {
        return Promise.reject(error);
      }*/
    }
    return Promise.reject(err);
  },
);

async function setAccessToken(res: any) {
  if (!res.config.url.includes('login')) {
    return;
  }

  const storeAccessToken = useTokenStore.getState().accessToken;
  if (storeAccessToken !== '') {
    return;
  }
  const { accessToken, accessTokenExpiresIn } = res.data;
  if (accessToken && accessTokenExpiresIn) {
    useTokenStore.setState({
      ...useTokenStore,
      accessToken: accessToken,
      expires: accessTokenExpiresIn,
    });
  }
  await handlerTokenExpires();
}
/**
 * @function
 * 유효시간 체크해서 access token을 다시 셋팅
 */
const handlerTokenExpires = async () => {
  const date = new Date(useTokenStore.getState().expires * 1000); // 비교할 unix time
  const now = new Date(); // 현재 시간

  const diffMSec = date.getTime() - now.getTime();
  const timeOut = setTimeout(async () => {
    try {
      const { data } = await app.get(`/reissue`, {
        withCredentials: true,
      });
      console.log('accessToken settimeout 재발급::', data);
      // 토큰을 다시 셋팅해줘야한다.
    } catch (e) {
      console.log('accessToken settimeout error::', e);
      return Promise.reject(e);
    } finally {
      clearTimeout(timeOut);
    }
  }, 1000);
};

const http = {
  get: app.get,
  post: app.post,
  delete: app.delete,
  put: app.put,
  patch: app.patch,
};

export default http;
