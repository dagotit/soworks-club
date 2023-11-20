import type { Metadata } from 'next';
import { Inter } from 'next/font/google';
import './globals.css';
import ReactQueryProvider from '@/components/ReactQueryProvider';
import DialogWrap from '@/components/DialogWrap';

const inter = Inter({ subsets: ['latin'] });

export const metadata: Metadata = {
  title: '다가치 프로젝트',
  description: 'Generated by create next app',
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="ko">
      <body className={inter.className}>
        <ReactQueryProvider>
          {children}
          <DialogWrap />
        </ReactQueryProvider>
      </body>
    </html>
  );
}
