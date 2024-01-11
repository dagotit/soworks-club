import styles from './ListItem.module.css';
export interface clubListItemProps {
  id: number; // 모임 id
  title: string; // 모임 제목
  date: any; // 모임일
  status: string; // 참여현황
}
const ListItem = (props: clubListItemProps) => {
  console.log('children', props);
  return (
    <li className={styles.itembox}>
      <p>{props.title}</p>
      <p>{props.date}</p>
      <span>{props.status}</span>
    </li>
  );
};
export default ListItem;
