import styles from './ListItem.module.css';
import { memo } from 'react';
import { useRouter } from 'next/navigation';
export interface clubListItemProps {
  id: number; // 모임 id
  title: string; // 모임 제목
  date: any; // 모임일
  status: string; // 참여현황
  images: string; // 모임이미지
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
    // TODO: 이건 ... 만들어서 연결
    router.push(`/detail/${id}`);
  }
  return (
    <li
      className={styles.itembox}
      onClick={() => onClickGotoDetailPage(props.id)}
    >
      <p>{props.title}</p>
      <p>{props.date}</p>
      <span>{props.status}</span>
      <img className={styles.itemBgImg} src={props.images} alt="이미지" />
    </li>
  );
});
export default ListItem;
