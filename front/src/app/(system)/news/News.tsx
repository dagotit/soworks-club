'use client';

import { useReceiveAlarmStore } from '@/store/useReceiveAlarm';
import { useEffect, useState } from 'react';
import { useGetAlarmList, useGetAlarmRead } from '@/hooks/useUser';

export type ALARM_DETAIL = {
  id: number | null;
  title: string;
  content: string;
  // "readYn": "Y"
}

const reset_alarm_detail = {
  id:null,
  title: '',
  content: ''
}
const News = () => {
  const {list, updateReadYN} = useReceiveAlarmStore();
  const [apiGet, setApiget] = useState(false);
  const getAlarmList = useGetAlarmList(apiGet);
  const [isShow, setIsShow] = useState(false);
  const apiGetAlarmRead = useGetAlarmRead();
  const [alarmDetail, setAlarmDetail] = useState<ALARM_DETAIL>(reset_alarm_detail)

  useEffect(() => {
    /* 페이지 최초 진입시 알람 리스트에 데이터 없으면 한번 호출..? */
    if (list.length === 0) {
      setApiget(true)
    }
    return () => {
      onClickAlarmClose()
    }
  }, []);

  /**
   * @function
   * 알람 제목 클릭 시 이벤트
   */
  function onClickAlarmItem(id: number) {
    apiGetAlarmRead.mutate(id, {
      onSuccess: (data) => setApiAlarmDetail(data, id)
    })
  }

  /**
   * @function
   * 알림 상세 내용
   */
  function setApiAlarmDetail(data: any, id:number) {
    if (data.respCode !== '00') {
      return;
    }
    const { respBody } = data;
    setIsShow(true); // 팝업 열기
    updateReadYN(id); // 읽음처리
    getAlarmList.refetch();
    setAlarmDetail({
      id: respBody.id,
      title: respBody.title,
      content: respBody.content
    })
  }

  function onClickAlarmClose() {
    setIsShow(false);
    setAlarmDetail(reset_alarm_detail);
  }

  if (list.length === 0) {
    return <div>리스트 없음</div>
  }

  return <div className="alarm-wrap">
    <ul>
      {list.map((item) => (<li key={item.id}>
        <button onClick={() => onClickAlarmItem(item.id)}>
          <p>{item.title}</p>
          {item.readYn === 'N' && <span>읽지않음</span>}
          {/*<p>{item.content}</p>*/}
        </button>
      </li>))}
    </ul>
    {/*알림 상세 팝업*/}
    {isShow && <div className="alarm-detail-popup-content-dimmed">
      <div className="alarm-detail-popup-content">
        <button onClick={onClickAlarmClose}></button>
        <p>{alarmDetail.title}</p>
        <p>{alarmDetail.content}</p>
      </div>
    </div>}
  </div>
}

export default News;