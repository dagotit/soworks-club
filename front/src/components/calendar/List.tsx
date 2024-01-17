import ListItem, { clubListItemProps } from '@/components/calendar/ListItem';
import { useRouter } from 'next/navigation';
import {
  FilterQueryParamType,
  useGetClubList,
  useGetMonthCalendar,
} from '@/hooks/useCalendar';
import { memo, useEffect, useState } from 'react';
import styles from './ListItem.module.css';
import { isEmptyObj } from '@/utils/common';
import { ClubListItemType, useClubListStore } from '@/store/useClubList';
import Link from 'next/link';
import useDidMountEffect from '@/utils/useDidMountEffect';

interface clubListProps {
  popupState: any;
  listFilterQueryData: FilterQueryParamType | {};
}

const List = memo((props: clubListProps) => {
  const router = useRouter();
  const { clubList, updateList } = useClubListStore();
  // const [list, setList] = useState<[] | ClubListItemType[]>([]);
  const { isLoading, isFetching, data } = useGetClubList(
    props.listFilterQueryData,
  );

  useEffect(() => {
    console.log('data:', isLoading);
  }, [data]);

  useEffect(() => {
    updateList([
      {
        id: 0,
        title: '모임1',
        date: '2022-12-23',
        status: '참여:1명',
        images: 'https://dummyimage.com/200x200',
      },
      {
        id: 1,
        title: '모임2',
        date: '2022-12-23',
        status: '참여:2명',
        images: 'https://dummyimage.com/200x200',
      },
      {
        id: 2,
        title: '모임3',
        date: '2022-12-23',
        status: '참여:3명',
        images: 'https://dummyimage.com/200x200',
      },
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
          ></Link>
        </div>
      )}
      {clubList.length !== 0 && (
        <ul>
          {clubList.map((value, index) => (
            <ListItem
              key={index}
              id={index}
              title={value.title}
              date={value.date}
              status={value.status}
              images={value.images}
            />
          ))}
        </ul>
      )}
    </div>
  );
});

export default List;
