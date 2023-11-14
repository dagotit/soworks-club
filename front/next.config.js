/** @type {import('next').NextConfig} */
const { PHASE_DEVELOPMENT_SERVER } = require('next/constants');

const devNextConfig = {
  reactStrictMode: false, // 애플리케이션 내에서 문제가 발생할 수 있는 부분에 대해 경고를 알려주는 기능
  swcMinify: true, //  Minifying 역할
  // async rewrites() {
  //   return [
  //     {
  //       source: '/auth/:path*',
  //       destination: `${process.env.NEXT_PUBLIC_API_URL}/auth/:path*`,
  //     },
  //   ];
  // },
  // trailingSlash: true,
};

const nextConfig = {
  reactStrictMode: true, // 애플리케이션 내에서 문제가 발생할 수 있는 부분에 대해 경고를 알려주는 기능
  swcMinify: true, //  Minifying 역할
};

module.exports = (phase, { defaultConfig }) => {
  if (phase === PHASE_DEVELOPMENT_SERVER) {
    /* 개발 환경 설정 구성 */
    return devNextConfig;
  }

  /* 개발 환경 제외한 환경 설정 구성 */
  return nextConfig;
};

// module.exports = nextConfig; // 기본 설정이었음..