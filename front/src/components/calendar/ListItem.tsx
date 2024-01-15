import styles from './ListItem.module.css';
import { memo } from 'react';
export interface clubListItemProps {
  id: number; // 모임 id
  title: string; // 모임 제목
  date: any; // 모임일
  status: string; // 참여현황
}

// 불필요한 리렌더링 막기
const ListItem = memo((props: clubListItemProps) => {
  console.log('children', props);
  return (
    <li className={styles.itembox}>
      <p>{props.title}</p>
      <p>{props.date}</p>
      <span>{props.status}</span>
    </li>
  );
});
export default ListItem;
