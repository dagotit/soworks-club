import React, { memo, useEffect, useState } from 'react';
import Image from 'next/image';
import { useGetAlarmList } from '@/hooks/useUser';
import { useTokenStore } from '@/store/useLogin';
import { isEmptyArr, isEmptyObj } from '@/utils/common';
import { useReceiveAlarmStore } from '@/store/useReceiveAlarm';


// eslint-disable-next-line react/display-name
const AlarmIcon = memo(()=> {
  const { accessToken } = useTokenStore();
  const getAlarmList = useGetAlarmList(!!accessToken);
  const {setList, list} = useReceiveAlarmStore();
  const [count, setCount] = useState<Number>(0);

  /**
   * @function
   * 알람 api > 스토어에 저장x
   */
  useEffect(() => {
    if (getAlarmList.isLoading) {
      return;
    }
    if (isEmptyObj(getAlarmList.data)) {
      return;
    }
    // @ts-ignore
    const { respBody } = getAlarmList.data;
    if (isEmptyArr(respBody)) {
      // 알람이 없는 경우
      return;
    }
    console.log('api 알람 "', respBody)
    setList(respBody);
  }, [getAlarmList.isLoading]);

  /**
   * @function
   * 알람 카운터 표시
   */
  useEffect(() => {
    console.log('알람 리스트', list)
    let readN = 0;
    list.forEach((item) => {
      if(item.readYn === 'N') {
        readN += 1
      }
    })
    setCount(readN);
  }, [list]);

  return <div>
    <Image src="/alert.svg" alt="알림" width={27} height={27} />
    {count !== 0 && <div>{ String(count) }</div>}
  </div>
})

export default AlarmIcon;