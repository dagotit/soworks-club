import axios from 'axios';

const app = axios.create({
  baseURL: `${process.env.NEXT_PUBLIC_API_URL}/auth`,
  withCredentials: true,
});

app.interceptors.request.use(
  (res) => {
    console.log('res', res);
    return res;
  },
  (err) => Promise.reject(err),
);

app.interceptors.response.use(
  (res) => res,
  async (err) => {
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

const http = {
  get: app.get,
  post: app.post,
  delete: app.delete,
  put: app.put,
  patch: app.patch,
};

export default http;
