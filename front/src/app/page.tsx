import { Fragment } from 'react';
import Main from '@/app/(main)/Main';
import Bg from '@/components/bgBox/Bg';
import Header from '@/components/Header';

export default function Home() {
  return (
    <Fragment>
      <Bg />
      <Main />
    </Fragment>
  )
}
