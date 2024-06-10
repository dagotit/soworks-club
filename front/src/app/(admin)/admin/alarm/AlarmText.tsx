import React, { memo, useState } from 'react';
import { alarmStartData, useAlarmStore } from '@/store/useAlarmList';
import { useDialogStore } from '@/store/useDialog';


// eslint-disable-next-line react/display-name
const AlarmText = memo(() => {
  const [title, setTitle] = useState<string>('');
  const [text, setText] = useState('');
  const [inputEl] = useState(null);
  const { setAlarmList, updateAlarmData }  = useAlarmStore();
  // const { open, allClose } = useDialogStore();


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
   * 알람 내용 스토어에 저장
   */
  function onBlurSetAlarmData() {
    if (title.trim() === '' && text.trim() === '') {
      return;
    }

    if (title.trim() === '') {
      // 알림 제목 없으면 내용 삭제
      updateAlarmData('all', alarmStartData);
      return;
    }
    const value = { title, text };
    setAlarmList('all', value);
  }

  return <div>
    <input type="text" ref={inputEl} onChange={onChangeInput} value={title} onBlur={onBlurSetAlarmData} />
    <textarea placeholder="알림 내용작성" onChange={onChangeTextarea} value={text} onBlur={onBlurSetAlarmData}></textarea>
  </div>
})

export default AlarmText;