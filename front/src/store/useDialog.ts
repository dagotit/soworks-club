import { create } from 'zustand';

type DialogType = 'alert' | 'confirm';
type DialogQueue = {
  type: DialogType;
  title: string;
  contents: string;
  action?: () => void;
};

interface DialogState {
  dialogList: DialogQueue[];
  open: (
    type: DialogType,
    title: string,
    contents: string,
    action?: () => void,
  ) => void;
  check: (action?: () => void) => void; // 확인
  cancel: () => void; // 취소
  allClose: () => void; // 전체 닫기
}

const useDialogStore = create<DialogState>((set, get) => ({
  dialogList: [],
  /**
   * @function
   * 알럿 팝업에 내용을 넣어서 오픈!
   */
  open: (
    type: DialogType,
    title: string,
    contents: string,
    action?: () => void,
  ) =>
    set((state) => ({
      dialogList: state.dialogList.concat({ type, title, contents, action }),
    })),
  /**
   * @function
   * 확인 버튼 클릭시 필요한 액션 실행후 삭제!
   */
  check: (action?: () => void) => {
    if (action) {
      action();
    }
    set((state) => {
      return {
        dialogList: state.dialogList.filter((item, idx) => {
          if (state.dialogList.length - 1 !== idx) {
            return item;
          }
        }),
      };
    });
  },
  /**
   * @function
   * 취소 버튼으로 아무런 액션 없이 삭제 '타입이 컨펌일 경우에만!'
   */
  cancel: () => {
    let popList = [...get().dialogList];
    popList.pop();
    set(() => ({
      dialogList: [...popList],
    }));
  },
  /**
   * @function
   * 페이지 이동시 열려있는 팝업닫기
   */
  allClose: () => {
    set(() => ({
      dialogList: [],
    }));
  },
}));

export { useDialogStore };
