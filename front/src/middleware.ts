import { NextResponse, NextFetchEvent } from 'next/server';
import type { NextRequest } from 'next/server';
import { isEmptyObj } from '@/utils/common'

type RESPONSE_ERROR = {
  respCode: string;
  respMsg: string;
  respBody: string;
};

/**
 * @function
 * 메인화면 진입시 로그인을 이미해서 토큰이 있는지 여부를 체크
 */
export async function withoutAuth(token: any) {
  const abortController = new AbortController();
  const { signal } = abortController;
  const timer = setTimeout(() => abortController.abort(), 5000);

  try {
    const response = await fetch(
      `${process.env.NEXT_PUBLIC_API_URL}/api/v1/auth/reissue`,
      {
        method: 'GET',
        mode: 'cors',
        credentials: 'include',
        signal,
        headers: {
          Cookie: `refreshToken=${token}`,
          'Content-Type': 'application/json',
        },
      },
    );
    // 여기 있는 any는 성공 시 변경 예정
    const resp: RESPONSE_ERROR | any = await response.json();
    clearTimeout(timer);

    return resp;
  } catch (e: any) {
    console.log('middleware reissue error');
    clearTimeout(timer);
    if (e.name === 'AbortError') {
      return 'AbortError';
    }
    return 'error';
  }
}

/**
 * @function
 * 관리자인지 체크하기
 */
export async function checkAdmin(token: any, refresh: any) {
  try {
    const response = await fetch(
      `${process.env.NEXT_PUBLIC_API_URL}/api/v1/member/check-admin`,
      {
        method: 'GET',
        mode: 'cors',
        credentials: 'include',
        headers: {
          Authorization: `Bearer ${token.accessToken}`,
          Cookie: `refreshToken=${refresh}`,
          'Content-Type': 'application/json',
        },
      },
    );
    // 여기 있는 any는 성공 시 변경 예정
    const resp: RESPONSE_ERROR | any = await response.json();

    if (!!resp && !isEmptyObj(resp) && resp.respCode === '00') {
      if (!isEmptyObj(resp.respBody) && resp.respBody.adminYn === 'Y') {
        return true;
      }
    }
    return false;
  } catch (e) {
    return false;
  }
}
/**
 * @function
 * 전체 페이지 진입시 진입 전에 실행되어진다.
 * 혹시 몰라서 전역으로 지정하였음..
 */
export async function middleware(request: NextRequest, event: NextFetchEvent) {
  const refreshToken = request.cookies.get('refreshToken')?.value;

  const { pathname, search } = request.nextUrl;

  // 메인화면 페이지 진입시 리프레시 토큰이 없을 경우 로그인 페이지로 이동
  if (!pathname.includes('login') && !refreshToken) {
    return NextResponse.redirect(new URL('/login', request.url));
  }

  const refererUrl = request.headers.get('referer');

  if (!pathname.includes('login') && refererUrl === null) {
    // 최초 진입일 경우
    const token = await withoutAuth(refreshToken);

    if (token.respCode === 'BIZ_018') {
      return NextResponse.redirect(new URL('/login', request.url));
    }
  }

  // 이미 로그인이 되어있다면 로그인, 비밀번호 찾기, 회원가입 페이지 진입 불가
  console.log('pathname', pathname)

  if (pathname === '/admin/') {
    // 어드민 페이지 진입시
    if (refererUrl !== null) {
      // 새로고침이 아닐경우 정상경로
      return NextResponse.next();
      // return NextResponse.redirect(new URL('/', request.url));
    }

    const token = await withoutAuth(refreshToken);

    console.log('middleware =======>', token)

    if (!!token && typeof token === 'string') {
      if (token === 'AbortError' || token === 'error') {
        // 타임아웃 걸렸을 경우
        const errorUrl = new URL('/error', request.url);
        errorUrl.searchParams.set('msg', token);

        return NextResponse.redirect(errorUrl);
      }
    }
    if (!!token && !isEmptyObj(token) && token.respCode === '00') {

      const isAdmin = await checkAdmin(token.respBody, refreshToken);

      if (!isAdmin) {
        // 어드민 사용자가 아니고 이전 페이지가 없는 경우 [ 새로고침 ]
        return NextResponse.redirect(new URL('/', request.url));
      }
    }

    return NextResponse.next();
  }
}

// /about/:path*는 *이 0개 이상이기 때문에 /about/a/b/c와 일치
export const config = {
  matcher: ['/', '/login/:path*', '/admin/'],
};
