'use client';
import styles from '@/components/css/Header.module.css';
import { useGetAccessToken, useGetLogout } from '@/hooks/useAuth';
import { useRouter } from 'next/navigation';
import React, { useEffect, useState } from 'react';
import { useTokenStore } from '@/store/useLogin';
import { useDialogStore } from '@/store/useDialog';
import useDidMountEffect from '@/utils/useDidMountEffect';
import { isEmptyObj } from '@/utils/common';
import Link from 'next/link';
import Image from 'next/image';

interface HeaderProps {
  propAttendanceCk?: () => void;
}
const Header = (props: any) => {
  const getLogout = useGetLogout();
  const router = useRouter();
  const { accessToken, setAccessToken, setTokenExpires } = useTokenStore();
  const [isNavOpen, setNavOpen] = useState(false);
  const getAccessToken = useGetAccessToken();
  const { open, allClose } = useDialogStore();
  const [isSearchOpen, setIsSearchOpen] = useState(false);
  const [searchVal, setSearchVal] = useState('');

  useEffect(() => {
    /*if (!accessToken) {
      getAccessToken.mutate(null, {
        onError: (err) => {
          console.log('err:::', err);
          open('alert', '로그아웃', '로그아웃 되었습니다.', () => {
            router.push('/login');
          });
        },
      });
    }*/
    return () => {
      allClose();
    };
  }, []);
  useDidMountEffect(() => {
    if (accessToken !== '' && !isEmptyObj(props)) {
      props.propAttendanceCk(true);
    }
  }, [accessToken]);
  /**
   * @function
   * 로그아웃 api 호출
   */
  function handleLogout() {
    getLogout.mutate(null, {
      onSuccess: () => {
        setAccessToken('');
        setTokenExpires(0);
        router.push('/login');
      },
      onError: () => {
        open('alert', '로그아웃', '로그아웃 실패');
      },
    });
  }
  /**
   * @function
   * 검색 열기 & 닫기
   */
  function onClickSearchOpen() {
    setIsSearchOpen(!isSearchOpen);
  }

  /**
   * @function
   * 메뉴 열기 & 닫기
   */
  function onClickNavOpen() {
    setNavOpen(!isNavOpen);
  }
  /**
   * @function
   * 검색 조회
   */
  function handleSearch(
    e:
      | React.KeyboardEvent<HTMLInputElement>
      | React.MouseEvent<HTMLButtonElement>
      | any,
  ) {
    if (e.type === 'click' || (e.type === 'keydown' && e.key === 'Enter')) {
      e.currentTarget.blur(); // input 포커스 아웃
      onClickSearchOpen();
    }
  }
  /**
   * @function
   * 검색 input
   */
  function onChangeSearch(e: React.ChangeEvent<HTMLInputElement>) {
    setSearchVal(e.currentTarget.value);
  }
  return (
    <header className={styles.header}>
      <div className={styles.headerWrap}>
        <div className={styles.menuTrigger} onClick={onClickNavOpen}>
          <Image src="/navBar.svg" alt="알림" width={18} height={12} />
        </div>
        <Link
          href={{
            pathname: '/news',
          }}
        >
          <Image src="/alert.svg" alt="알림" width={27} height={27} />
        </Link>

        <h1>
          <Image src="/Logo.svg" alt="다가치" width={91} height={28} />
        </h1>
        <button type="button" onClick={onClickSearchOpen}>
          <Image src="/search.svg" alt="검색" width={23} height={23} />
        </button>
      </div>

      <nav className={`${styles.slideMenu} ${isNavOpen ? styles.active : ''}`}>
        <div
          className={`${styles.menuTrigger} ${isNavOpen ? '' : styles.display}`}
          onClick={onClickNavOpen}
        >
          메뉴닫기
        </div>
        <ul>
          <li className={styles.timeline}>
            <Link
              href={{
                pathname: '/create',
              }}
            >
              모임생성하기
            </Link>
          </li>
          <li className={styles.events}>
            <Link
              href={{
                pathname: '/calendar',
              }}
            >
              모임보러가기
            </Link>
          </li>
          <li className={styles.calendar}>Calendar</li>
          <li className={`${styles.sep} ${styles.settings}`}>Settings</li>
          <li className={styles.logout}>
            <button type="button" onClick={handleLogout}>
              로그아웃
            </button>
          </li>
        </ul>
      </nav>

      <div
        className={`${styles.searchWrap} ${isSearchOpen ? styles.active : ''}`}
      >
        <input
          type="text"
          value={searchVal}
          onChange={onChangeSearch}
          onKeyDown={handleSearch}
        />
        <button type="button" onClick={handleSearch}>
          검색
        </button>
      </div>
    </header>
  );
};
export default Header;
