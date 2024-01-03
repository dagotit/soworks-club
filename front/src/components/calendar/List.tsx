import ListItem from '@/components/calendar/ListItem';

const List = () => {
  function onClickFilterPopup() {
    console.log('click');
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
