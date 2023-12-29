import { NextResponse, NextFetchEvent } from 'next/server';
import type { NextRequest } from 'next/server';

type RESPONSE_ERROR = {
  respCode: string;
  respMsg: string;
  respBody: string;
};

/**
 * @function
 * 메인화면 진입시 로그인을 이미해서 토큰이 있는지 여부를 체크
 */
export async function withoutAuth(req: NextRequest) {
  const abortController = new AbortController();
  const { signal } = abortController;
  const timer = setTimeout(() => abortController.abort(), 5000);

  try {
    const response = await fetch(
      `${process.env.NEXT_PUBLIC_API_URL}/auth/reissue`,
      {
        method: 'GET',
        mode: 'cors',
        credentials: 'include',
        signal,
        headers: {
          'Content-Type': 'application/json',
        },
      },
    );
    // 여기 있는 any는 성공 시 변경 예정
    const resp: RESPONSE_ERROR | any = await response.json();
    clearTimeout(timer);
    if (resp.respCode === 'BIZ_001') {
      // 서버에러
      const errorUrl = new URL('/error', req.url);
      errorUrl.searchParams.set('msg', resp.respMsg);

      return NextResponse.redirect(errorUrl);
    }
    if (response.status === 401) {
      // 인증정보 없음
      return NextResponse.redirect(new URL('/login', req.url));
    }
    if (response.status === 200) {
      return NextResponse.next();
    }
  } catch (e: any) {
    console.log('middleware reissue error');
    clearTimeout(timer);
    if (e.name === 'AbortError') {
      // 타임아웃 걸렸을 경우
      const errorUrl = new URL('/error', req.url);
      errorUrl.searchParams.set('msg', 'time_out');

      return NextResponse.redirect(errorUrl);
    }
    throw e;
  }
}
/**
 * @function
 * 전체 페이지 진입시 진입 전에 실행되어진다.
 * 혹시 몰라서 전역으로 지정하였음..
 */
export async function middleware(request: NextRequest, event: NextFetchEvent) {
  const refreshToken = request.cookies.get('refreshToken')?.value;

  // 메인화면 페이지 진입시 리프레시 토큰이 없을 경우 로그인 페이지로 이동
  if (request.url === `${process.env.NEXT_PUBLIC_DOMAIN}` && !refreshToken) {
    return NextResponse.redirect(new URL('/login', request.url));
  }

  /*  if (request.url === `${process.env.NEXT_PUBLIC_DOMAIN}` && refreshToken) {
    return await withoutAuth(request);
  }*/

  // 이미 로그인이 되어있다면 로그인, 비밀번호 찾기, 회원가입 페이지 진입 불가
  // if (!!refreshToken) {
  //   if (
  //     request.url === `${process.env.NEXT_PUBLIC_DOMAIN}login/` ||
  //     request.url === `${process.env.NEXT_PUBLIC_DOMAIN}passfind/` ||
  //     request.url === `${process.env.NEXT_PUBLIC_DOMAIN}join/`
  //   )
  //     return NextResponse.redirect(new URL('/', request.url));
  // }
}

export const config = {
  // matcher: '/about/:path*',
  matchers: ['/', '/login/:path*'],
};
