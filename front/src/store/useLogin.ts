import { create } from 'zustand';

interface LoginInfo {
  email: string;
  password: string;
  handleInputId: (id: string) => void;
}
const useLoginStore = create<LoginInfo>((set) => ({
  email: '',
  password: '',
  handleInputId: (id) => set((state) => ({ ...state, id })),
}));

export { useLoginStore };
