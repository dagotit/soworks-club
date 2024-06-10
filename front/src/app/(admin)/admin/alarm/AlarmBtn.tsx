'use client';

import { Fragment, memo, useState } from 'react';
import AlarmPopup from '@/components/popups/AlarmPopup';
import { alarmStartData, useAlarmStore } from '@/store/useAlarmList';

interface useListProps {
  receiveId: number | undefined;
}
// eslint-disable-next-line react/display-name
const AlarmBtn = memo((props: useListProps) => {
  const [isShow, setIsShow] = useState(false);
  const { setAlarmList, deleteAlarmItem } = useAlarmStore();
  function onClickAlarmPopup() {
    // TODO 확인 필요?
    setIsShow(!isShow)
    if (!isShow && props.receiveId) {
      setAlarmList(props.receiveId, alarmStartData);
      return;
    }
    if (isShow && props.receiveId) {
      deleteAlarmItem(props.receiveId);
    }
  }

  return <Fragment>
          {!isShow && <button type="button" onClick={onClickAlarmPopup}>알림작성</button>}
          <AlarmPopup isShow={isShow} onClose={onClickAlarmPopup} receiveId={props.receiveId} />
        </Fragment>
})

export default AlarmBtn;