import {create} from 'zustand';

interface AlarmListState {
  alarmList: any; // new Map()
  setAlarmList: (key: number | 'all', val?: any) => void;
  updateAlarmData: (key: number | 'all', val: any) => void;
  deleteAlarmItem: (key: number) => void;
  resetAlarmList: () => void;
}
/* 최초 시작 데이터 */
export const alarmStartData = { title: '', text: '' };

const useAlarmStore = create<AlarmListState>(((set, get)=>({
  alarmList: new Map(),
  /**
   * @function
   * 알림 작성 버튼 클릭시 map 에 알림을 보낼 해당 계정의 id를 담는다.
   */
  setAlarmList: (key, val) => {
    const newItems = new Map(get().alarmList);
    newItems.set(key, val);

    set({ alarmList: newItems });
    // console.log('추가:',get().alarmList)
  },
  /**
   * @function
   * 알림의 내용이 입력되면 blur 할 때 내용 입력
   */
  updateAlarmData: (key, val) => {
    const newMap = new Map(get().alarmList);
    if (newMap.has(key)) {
      newMap.delete(key)
      newMap.set(key, val)
      set({ alarmList: newMap });
      // console.log('업데이트:',get().alarmList)
    }
  },
  /**
   * @function
   * map 에서 member ID 삭제
   */
  deleteAlarmItem: (key: number) => {
    const newMap = new Map(get().alarmList);
    if (newMap.has(key)) {
      newMap.delete(key)
      set({ alarmList: newMap });
      // console.log('삭제:',get().alarmList)
    }
  },
  resetAlarmList: () => {
    get().alarmList.clear();
    set({ alarmList: new Map() });
    // console.log('추가:',get().alarmList)
  }
})))

export { useAlarmStore };