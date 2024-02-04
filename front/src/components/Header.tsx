'use client';
import styles from '@/components/css/Header.module.css';
import { useGetAccessToken, useGetLogout } from '@/hooks/useAuth';
import { usePathname, useRouter } from 'next/navigation';
import React, { useEffect, useState } from 'react';
import { useTokenStore } from '@/store/useLogin';
import { useDialogStore } from '@/store/useDialog';
import useDidMountEffect from '@/utils/useDidMountEffect';
import { isEmptyObj } from '@/utils/common';
import Link from 'next/link';
import Image from 'next/image';
import { useGetSearchList } from '@/hooks/useGroup';
import { useGetAdminCheck } from '@/hooks/useAdmin';

const Header = (props: any) => {
  const getLogout = useGetLogout();
  const router = useRouter();
  const pathname = usePathname();
  const { setAccessToken, setTokenExpires } = useTokenStore();
  const [isNavOpen, setNavOpen] = useState(false);
  const { open, allClose } = useDialogStore();
  const [isSearchOpen, setIsSearchOpen] = useState(false);
  const [searchVal, setSearchVal] = useState('');
  const [isShowBackBtn, setIsShowBackBtn] = useState(
    pathname.includes('/group/detail'),
  );
  // 검색
  const apiSearch = useGetSearchList();
  const [searchList, setSearchList] = useState<any[]>([]);
  // 어드민 여부 체크 [ 메뉴 노출 여부 ]
  const apiAdminCheck = useGetAdminCheck();
  const [isAdmin, setIsAdmin] = useState(false);

  useEffect(() => {
    return () => {
      allClose();
    };
  }, []);

  useEffect(() => {
    if (!!apiAdminCheck.data && !isEmptyObj(apiAdminCheck.data)) {
      // @ts-ignore
      const respBody = apiAdminCheck.data.respBody;
      setIsAdmin(respBody.adminYn === 'Y');
    }
  }, [apiAdminCheck.data]);

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
    if (e.type === 'click' || (e.type === 'keypress' && e.key === 'Enter')) {
      e.currentTarget.blur(); // input 포커스 아웃
      apiSearch.mutate(searchVal, {
        onSuccess: handleSearchSuccess,
      });
      // onClickSearchOpen();
    }
  }
  /**
   * @function
   * api 검색 성공
   **/
  function handleSearchSuccess(e: any) {
    console.log('e:', e);
    if (e.respCode === '00') {
      const list = e.respBody.map((item: any, index: number) => {
        if (index < 6) {
          return item;
        }
      });
      console.log(list);
      setSearchList(list);
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
        {isShowBackBtn && (
          <div
            className={styles.backBtn}
            onClick={() => {
              history.back();
            }}
          >
            &#10094;
          </div>
        )}
        {!isShowBackBtn && (
          <div className={styles.menuTrigger} onClick={onClickNavOpen}>
            <Image src="/navBar.svg" alt="알림" width={18} height={12} />
          </div>
        )}
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
          {isAdmin && (
            <li className={styles.calendar}>
              <Link
                href={{
                  pathname: '/admin',
                }}
              >
                admin
              </Link>
            </li>
          )}

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
        <div className={styles.searchAction}>
          <input
            type="text"
            value={searchVal}
            onChange={onChangeSearch}
            onKeyPress={handleSearch}
          />
          <button type="button" onClick={handleSearch}>
            검색
          </button>
        </div>
        {searchList.length !== 0 && (
          <div>
            <ul>
              {searchList.map((value, index) => (
                <li key={index}>
                  <Link
                    href={{
                      pathname: `/group/detail/${value.groupId}`,
                    }}
                  >
                    {value.category} | {value.name}
                    {value.status === 'WAITING' ? '모집중' : '모집종료'}
                  </Link>
                </li>
              ))}
              <li>
                <Link
                  href={{
                    pathname: '/calendar',
                  }}
                >
                  더보기
                </Link>
              </li>
            </ul>
          </div>
        )}
      </div>
    </header>
  );
};
export default Header;
