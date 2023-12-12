'use client';

const Header = () => {
  /**
   * @function
   * 로그아웃 api 호출
   */
  function handleLogout() {}
  return (
    <header>
      <button type="button" onClick={handleLogout}>
        로그아웃
      </button>
    </header>
  );
};
export default Header;
