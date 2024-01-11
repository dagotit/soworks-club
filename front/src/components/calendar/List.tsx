import ListItem, { clubListItemProps } from '@/components/calendar/ListItem';
import { useRouter } from 'next/navigation';
import { useGetMonthCalendar } from '@/hooks/useCalendar';
import { useEffect, useState } from 'react';
import styles from './ListItem.module.css';
import { isEmptyObj } from '@/utils/common';

export interface clubListProps {
  popupState: any;
}
const List = (props: clubListProps) => {
  const router = useRouter();
  const [list, setList] = useState<[] | clubListItemProps[]>([]);
  /*  const { isLoading, isFetching, data, isError, error, refetch } =
    useGetMonthCalendar(1);

  useEffect(() => {
    console.log('isError:', error);
  }, [error]);*/
  useEffect(() => {
    setList([
      { id: 0, title: '모임1', date: '2022-12-23', status: '참여:1명' },
      { id: 1, title: '모임2', date: '2022-12-23', status: '참여:2명' },
      { id: 2, title: '모임3', date: '2022-12-23', status: '참여:3명' },
    ]);
  }, []);
  /**
   * @function
   * 팝업 열기 버튼
   */
  function onClickFilterPopup() {
    if (!isEmptyObj(props)) {
      props.popupState(true);
    }
  }

  if (list.length === 0) {
    return <div className={styles.listWrap}>모임이 없습니다.</div>;
  }

  return (
    <div className={styles.listWrap}>
      <div className={styles.clubFilter}>
        <button type="button" onClick={onClickFilterPopup}>
          필터 조절하기
        </button>
      </div>
      <div className={styles.clubInfo}>
        <p>모임 일정</p>
        <span>총 {list.length}개의 모임</span>
      </div>
      <ul>
        {list.map((value, index) => (
          <ListItem
            key={index}
            id={index}
            title={value.title}
            date={value.date}
            status={value.status}
          />
        ))}
      </ul>
    </div>
  );
};

export default List;
