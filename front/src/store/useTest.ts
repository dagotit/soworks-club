import { create } from 'zustand';

interface LoginInfo {
  id: string;
  password: string;
  handleInputId: (id: string) => void;
}
const useLoginStore = create<LoginInfo>((set) => ({
  id: '',
  password: '',
  handleInputId: (id) => set((state) => ({ ...state, id })),
}));

export { useLoginStore };
