import { NextResponse, NextFetchEvent } from 'next/server';
import type { NextRequest } from 'next/server';

/**
 * @function
 * 메인화면 진입시 로그인을 이미해서 토큰이 있는지 여부를 체크
 */
export async function withoutAuth(req: NextRequest) {
  try {
    const response = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/reissue`, {
      method: 'GET',
      mode: 'cors',
      credentials: 'include',
      headers: {
        'Content-Type': 'application/json',
      },
    });
    const state = response.status;
    if (state === 401) {
      return NextResponse.redirect(new URL('/login', req.url));
    }
    if (state === 200) {
      return NextResponse.next();
    }
  } catch (e) {
    console.log('middleware reissue error');
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
  // 메인화면 페이지 진입시 리프레시 토큰이 없을 경우만 reissue api를 호출
  if (request.url === `${process.env.NEXT_PUBLIC_DOMAIN}` && !refreshToken) {
    return await withoutAuth(request);
  }

  // 이미 로그인이 되어있다면 로그인 페이지 진입 불가
  if (
    request.url === `${process.env.NEXT_PUBLIC_DOMAIN}login/` &&
    !!refreshToken
  ) {
    return NextResponse.redirect(new URL('/', request.url));
  }
}

export const config = {
  // matcher: '/about/:path*',
  matchers: ['/', '/login/:path*'],
};
