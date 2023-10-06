import Head from 'next/head';
import '@/styles/globals.css'
import { ThemeProvider } from "@/components/theme-provider"
import { ClerkProvider } from "@clerk/nextjs";

import type { AppProps } from 'next/app'
import { Toaster } from '@/components/ui/toaster';
import {LoadingProvider} from '../custom components/LoadingContext';

export default function App({ Component, pageProps }: AppProps) {
  return (
    <div >
      <Head>
        <title>Rentalux</title>
      </Head>
      <Toaster />
      <ThemeProvider
        attribute="class"
        defaultTheme="light"
      >

      <ClerkProvider {...pageProps}>
        <LoadingProvider>
        <Component {...pageProps} />
        </LoadingProvider>
      </ClerkProvider>
      </ThemeProvider>
    </div>
  );
}
