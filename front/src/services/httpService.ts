import axios from 'axios';
import { NextResponse } from 'next/server';
import { useTokenStore } from '@/store/useLogin';
import { isEmptyObj } from '@/utils/common';

const response = NextResponse.next();
let timeOut: undefined | NodeJS.Timeout = undefined;
const app = axios.create({
  // baseURL: `${process.env.NEXT_PUBLIC_API_URL}/auth`,
  withCredentials: true,
  timeout: 30000,
  headers: {
    'Content-Type': 'application/json'
  }
});

let count = 0;

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
    const url = res.config.url;
    const respData = res.data.respBody;
    const { respCode } = res.data;

    if (!!respCode && respCode === 'BIZ_018') {
      // 회원이 존재하지 않는 경우
      location.href = `/login?code=error`;
      return Promise.reject(res);
    }
    /** access token **/
    await setAccessToken(url, respData);
    if (url && url.includes('admin/template')) {
      return res
    }

    console.log('확인용!!!!!!!!!!!!!!!', res.data)

    return await res.data;
  },
  async (err) => {
    console.log('temp::::', err)
    const { data, status } = err.response;
    const { url } = err.config;
    if (!isEmptyObj(data)) {
      const code = data?.respCode;
      if (code === 'BIZ_007' && url === '/api/v1/auth/login') {
        // 로그인 계정 없을 경우 [ 로그인 api 호출 시에 ]
        return Promise.reject(err);
      }


      // login api 가 아닐 경우에만 401 에러를 처리한다
      const originalConfig = err.config;

      if (status === 401 && !originalConfig._retry && count === 0) {
        if (code === 'BIZ_007' || data instanceof Blob) {
          // count === 0 여러면 reissue 호출하는걸 방지 useQueries 사용하면 해당 현상이 발생할 일이 없음. 아마도?
          originalConfig._retry = true;
          const tokenResp = await apiGetRefreshToken()
          if (tokenResp) {
            return await app(originalConfig);
          }
          return
        }
      }
    }

    if (String(status).startsWith('5')) {
      // 서버 오류나면 에러 페이지로 이동
      location.href = `${process.env.NEXT_PUBLIC_DOMAIN}error?msg=${err.response.data.respMsg}`;
    }

    return Promise.reject(err);
  },
);

async function apiGetRefreshToken() {
  try {
    count = 1;
    const resp = await axios.get(`/api/v1/auth/reissue`, {
      withCredentials: true,
    });
    // TODO 왜 일치하지 않은지 확인 필요..
    console.log('리프레시 토큰 resp ==================>>', resp.data)
    if (isEmptyObj(resp)) {
      return
    }

    const url = resp.config.url;
    const { respBody, respCode } = resp.data;

    if (respCode === 'BIZ_018') {
      location.href = `/login?code=error`;
    }

    // access token 다시 담기
    await setAccessToken(url, respBody);
    // return app(originalConfig);
    return true
  } catch (error: any) {
    const code = error.response.data.respCode;
    // BIZ_002 런타임 오류?
    if (code === 'BIZ_002' || code === 'BIZ_018') {
      // 로그아웃된 사용자 입니다. & BIZ_018 로그아웃 사용자
      location.href = `/login?code=error`;
    }
    return Promise.reject(error);
  }
}

async function setAccessToken(requestUrl: string | undefined, respData: any) {
  if (!requestUrl) {
    return;
  }
  if (!requestUrl.includes('login') && !requestUrl.includes('reissue')) {
    return
  }

  if (useTokenStore.getState().accessToken !== '') {
    return;
  }

  const { accessToken, accessTokenExpiresIn } = respData;
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
      await app.get(`/api/v1/auth/reissue`, {
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
