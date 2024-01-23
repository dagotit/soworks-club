import ListItem from '@/components/calendar/ListItem';
import { useRouter } from 'next/navigation';
import { memo } from 'react';
import styles from './ListItem.module.css';
import { isEmptyObj } from '@/utils/common';
import { useClubListStore } from '@/store/useClubList';
import Link from 'next/link';

interface clubListProps {
  popupState: any;
}

const List = memo((props: clubListProps) => {
  const router = useRouter();
  const { clubList } = useClubListStore();

  /**
   * @function
   * 팝업 열기 버튼
   */
  function onClickFilterPopup() {
    if (!isEmptyObj(props)) {
      props.popupState(true);
    }
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
        <span>총 {clubList.length}개의 모임</span>
      </div>
      {clubList.length === 0 && (
        <div className={styles.listWrap}>
          <p>모임이 없습니다.</p>
          <p>모임장이 되어 모임을 만들어보세요.</p>
          <Link
            className={styles.create}
            href={{
              pathname: '/create',
            }}
          >
            모임 만들기
          </Link>
        </div>
      )}
      {clubList.length !== 0 && (
        <ul>
          {clubList.map((value, index) => (
            <ListItem
              key={index}
              id={value.id}
              title={value.title}
              date={value.date}
              status={value.status}
              personNumber={value.personNumber}
              images={value.images}
              time={value.time}
            />
          ))}
        </ul>
      )}
    </div>
  );
});

export default List;
