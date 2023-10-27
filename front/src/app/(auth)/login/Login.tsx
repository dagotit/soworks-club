'use client';
import { useLoginStore } from '@/store/useTest';
import { useGetUser } from '@/hooks/useAuth';

const Login = () => {
  const { id } = useLoginStore();
  const { data, isLoading } = useGetUser();

  return <main>로그인</main>;
};
export default Login;
