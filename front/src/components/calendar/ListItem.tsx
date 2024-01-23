import styles from './ListItem.module.css';
import { memo } from 'react';
import { useRouter } from 'next/navigation';
export interface clubListItemProps {
  id: number; // 모임 id
  title: string; // 모임 제목
  date: any; // 모임일
  status: string; // 모임 상태
  images: string; // 모임이미지
  personNumber: number; // 참여한 인원수
  time: string; // 모임 시간
}

// 불필요한 리렌더링 막기
const ListItem = memo((props: clubListItemProps) => {
  console.log('children', props);
  const router = useRouter();

  /**
   * @function
   * 모임 상세 페이지로 이동
   */
  function onClickGotoDetailPage(id: number) {
    router.push(`/group/detail/${id}`);
  }
  return (
    <li
      className={`${styles.itembox} ${
        props.status !== 'WAITING' ? styles.disabled : ''
      }`}
      onClick={() => onClickGotoDetailPage(props.id)}
      style={{ backgroundImage: `url(${props.images})` }}
    >
      <p>{props.title}</p>
      <p>{props.date}</p>
      <p>{props.time}</p>
      <span>{props.personNumber}</span>
    </li>
  );
});
export default ListItem;
