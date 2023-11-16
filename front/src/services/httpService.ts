import axios from 'axios';
import { NextResponse } from 'next/server';
import { useTokenStore } from '@/store/useLogin';
const response = NextResponse.next();

const app = axios.create({
  baseURL: `${process.env.NEXT_PUBLIC_API_URL}/auth`,
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
  (res) => {
    /** access token **/
    setAccessToken(res);
    return res.data;
  },
  async (err) => {
    console.log('response err:', err);
    const originalConfig = err.config;
    if (err.response.status === 401 && !originalConfig._retry) {
      originalConfig._retry = true;
      // 토큰 다시 가져오기
      //   try {
      //     const { data } = await axios.get(
      //       `${process.env.NEXT_PUBLIC_API_URL}/user/refresh-token`,
      //       { withCredentials: true },
      //     );
      //     if (data) return app(originalConfig);
      //   } catch (error) {
      //     return Promise.reject(error);
      //   }
    }
    return Promise.reject(err);
  },
);

function setAccessToken(res: any) {
  if (!res.config.url.includes('login')) {
    return;
  }

  const accessToken = useTokenStore.getState().accessToken;
  if (accessToken !== '') {
    return;
  }
  if (res.data.accessToken && res.data.accessTokenExpiresIn) {
    useTokenStore.setState({
      ...useTokenStore,
      accessToken: res.data.accessToken,
      expires: res.data.accessTokenExpiresIn,
    });
  }
  handlerTokenExpires();
}

const handlerTokenExpires = async () => {
  const date = new Date(useTokenStore.getState().expires * 1000); // 비교할 unix time
  const now = new Date(); // 현재 시간

  const diffMSec = date.getTime() - now.getTime();
  const timeOut = setTimeout(async () => {
    try {
      const { data } = await axios.get(
        `${process.env.NEXT_PUBLIC_API_URL}/auth/reissue`,
        { withCredentials: true },
      );
      console.log('settimeout::', data);
    } catch (e) {
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
