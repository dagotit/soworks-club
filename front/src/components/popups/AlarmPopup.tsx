'use client';

import React, { memo, useState } from 'react';
import { alarmStartData, useAlarmStore } from '@/store/useAlarmList';

type AlarmProps = {
  isShow : Boolean;
  onClose: () => void;
  receiveId: number | undefined;
}

// eslint-disable-next-line react/display-name
const AlarmPopup = memo((props:AlarmProps) => {
  const [title, setTitle] = useState<string>('');
  const [text, setText] = useState('');
  const { updateAlarmData } = useAlarmStore();

  if (!props.isShow) {
    return;
  }


  /**
   * @function
   * 내용 스토어에 저장..
   */
  function onBlurSetAlarmList() {
    if (!props.receiveId) {
      return;
    }
    if (title.trim() === '') {
      // 알림 제목 없으면 내용 삭제
      updateAlarmData(props.receiveId, alarmStartData)
      return;
    }
    if (title.trim() === '' && text.trim() === '') {
      return;
    }
    const value = { title, text };
    updateAlarmData(props.receiveId, value)
  }

  /**
   * @function
   * 알림 제목
   */
  function onChangeInput(e: React.ChangeEvent<HTMLInputElement>) {
    setTitle(e.currentTarget.value);
  }

  /**
   * @function
   * 알림 내용
   */
  function onChangeTextarea(e: React.ChangeEvent<HTMLTextAreaElement>) {
    setText(e.currentTarget.value);
  }

  /**
   * @function
   * 알림 작성화면 삭제하면.. 내용도 삭제
   */
  function onClickPopupClose() {
    setText('');
    setTitle('');
    props.onClose();
  }

  return <div>
          <div>
            <input type="text" onBlur={onBlurSetAlarmList} onChange={onChangeInput} value={title} />
            <textarea placeholder="알림 내용작성" onChange={onChangeTextarea} value={text} onBlur={onBlurSetAlarmList}></textarea>
          </div>
          <button type="button" onClick={onClickPopupClose}>닫기</button>
         </div>
});

export default AlarmPopup;