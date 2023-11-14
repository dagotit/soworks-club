import { create } from 'zustand';

interface TokenInfo {
  accessToken: string;
  expires: number;
  setAccessToken: (token: string) => void;
  setTokenExpires: (expires: number) => void;
}
const useTokenStore = create<TokenInfo>((set, get) => ({
  accessToken: '',
  expires: 0,
  setAccessToken: (accessToken: string) =>
    set((state) => ({ ...state, accessToken })),
  setTokenExpires: (expires: number) => {
    set((state) => ({ ...state, expires }));
  },
}));

export { useTokenStore };
