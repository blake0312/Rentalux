import Image from 'next/image'
import  Menu  from '../custom components/Menu'
import { Button } from '@/components/ui/button'
import Footer from '../custom components/Footer'
import Link from 'next/link'

export default function Home() {
  return (
    <main className="">
      <nav className="p-6 space-x-6 ">
        <Menu />
      </nav>
      <div className="flex flex-col lg:flex-row">
     
        <div className="flex-1 lg:mt-0">
          <section className="py-12 flex flex-col items-center text-center gap-6 mt-2">
            <h1 className="text-4xl font-bold font-sans">Welcome To Rentalux</h1>
            <p className="text-2xl text-muted-foreground max-w-2xl font-serif">
              Experience the ultimate convenience of our car rental service, where we simplify your journey with an effortless booking process designed with ease of use.
            </p>
          </section>
          <div className="flex gap-2 items-center justify-center">
            <Link href="/vehicles">
              <Button>Browse Now</Button>
            </Link>
          </div>
        </div>

        
        <div className="flex-1 lg:-mt-12">
          <div className="car__image-container">
            <div className={`car__image slide-from-right`}>
              <Image src="/r34.png" alt="hero"
                fill sizes="(max-width: 640px) 100vw, (max-width: 1024px) 50vw, 25vw"
                priority={true}
                className="object-contain"
                unoptimized={true}
              />
            </div>
          </div>
        </div>
      </div>
      <Footer />
    </main>
  )
}
