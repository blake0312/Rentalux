import Head from 'next/head';
import '@/styles/globals.css'
import { ThemeProvider } from "@/components/theme-provider"
import { ClerkProvider } from "@clerk/nextjs";

import type { AppProps } from 'next/app'
import { Toaster } from '@/components/ui/toaster';

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
        <Component {...pageProps} />
      </ClerkProvider>
      
      </ThemeProvider>
    </div>
  );
}
