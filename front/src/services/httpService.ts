import axios from 'axios';
import { NextResponse } from 'next/server';
import { useTokenStore } from '@/store/useLogin';
import { isEmptyObj } from '@/utils/common';

const response = NextResponse.next();
let timeOut: undefined | NodeJS.Timeout = undefined;
const app = axios.create({
  // baseURL: `${process.env.NEXT_PUBLIC_API_URL}/auth`,
  // baseURL: `/`,
  withCredentials: true,
  timeout: 30000,
});

app.interceptors.request.use(
  (res) => {
    const accessToken = useTokenStore.getState().accessToken;
    if (accessToken) {
      res.headers['Authorization'] = `Bearer ${accessToken}`;
    }
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
    if (!isEmptyObj(err.response.data)) {
      const data = err.response.data;
      if (data.respCode === 'BIZ_007') {
        const originalConfig = err.config;
        if (err.response.status === 401 && !originalConfig._retry) {
          originalConfig._retry = true;
          // 토큰 다시 가져오기 TODO: 오류가 많이 생길 것 같아서 이건 ... 회의 필요
          try {
            const resp = await axios.get(`/auth/reissue`, {
              withCredentials: true,
            });
            if (resp) {
              // access token 다시 담기
              await setAccessToken(resp);
              return app(originalConfig);
            }
          } catch (error) {
            return Promise.reject(error);
          }
        }
      }
    }

    const code = String(err.response.status);
    if (code.startsWith('5')) {
      location.href = `${process.env.NEXT_PUBLIC_DOMAIN}error?msg=${err.response.data.respMsg}`;
    }
    return Promise.reject(err);
  },
);

async function setAccessToken(res: any) {
  const requestUrl = res.config.url;
  if (requestUrl.includes('login') || requestUrl.includes('reissue')) {
    const storeAccessToken: string = useTokenStore.getState().accessToken;
    if (storeAccessToken !== '') {
      return;
    }
    const { accessToken, accessTokenExpiresIn } = res.data.respBody;
    if (!!accessToken && !!accessTokenExpiresIn) {
      useTokenStore.setState({
        ...useTokenStore,
        accessToken: accessToken,
        expires: accessTokenExpiresIn,
      });

      if (timeOut != undefined) {
        clearTimeout(timeOut);
        timeOut = undefined;
      }
      await handlerTokenExpires();
    }
  }
}
/**
 * @function
 * 유효시간 체크해서 access token을 다시 셋팅
 */
const handlerTokenExpires = async () => {
  const date = new Date(useTokenStore.getState().expires * 1000); // 비교할 unix time
  const now = new Date(); // 현재 시간

  const diffMSec: number = date.getTime() - now.getTime();
  timeOut = setTimeout(async () => {
    try {
      useTokenStore.setState({
        ...useTokenStore,
        accessToken: '',
        expires: 0,
      });
      await app.get(`/reissue`, {
        withCredentials: true,
      });
    } catch (e) {
      await Promise.reject(e);
    }
    // 25분으로 해둠.. diffMSec가 초로 들어가지 않는 오류가 있어서 .. ㅜㅜ
  }, 1500000);
};

const http = {
  get: app.get,
  post: app.post,
  delete: app.delete,
  put: app.put,
  patch: app.patch,
};

export default http;
