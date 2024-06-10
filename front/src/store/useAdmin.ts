import {create} from 'zustand';

export type ListItemType = {
  "email": string;
  "name": string;
  "nickname": string;
  "memberId":number;
  "profileImage": string;
  "companyName": string;
}
interface UserListState {
  userList: ListItemType[];
  setUserList: (list: ListItemType[]) => void;
  resetUserList: () => void;
}

const useAdminStore = create<UserListState>((set) => ({
  userList: [],
  setUserList: (list) => {
    set(() => ({
      userList: [...list]
    }))
  },
  resetUserList: () => {
    set(() => ({ userList: [] }));
  }
}))

export  { useAdminStore };