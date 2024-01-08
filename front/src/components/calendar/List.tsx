import ListItem from '@/components/calendar/ListItem';
import { useRouter } from 'next/navigation';

const List = () => {
  const router = useRouter();
  function onClickFilterPopup() {
    console.log('click');
    router.push('/'); // test를 위한 임시
  }

  return (
    <div>
      <button type="button" onClick={onClickFilterPopup}>
        필터 조절하기
      </button>
      <div>
        <p>모임 일정</p>
        <span>총 4개의 모임</span>
      </div>
      <ul>
        {[1, 2, 3, 4].map((value, index) => (
          <ListItem key={index} />
        ))}
      </ul>
    </div>
  );
};

export default List;
