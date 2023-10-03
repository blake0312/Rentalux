import Link from "next/link";
import Image from 'next/image'
export default function logo(){
    return (
        <div>
        <Link href='/' className='flex justify-center items-center'>
        <Image
          src='/RL2.svg'
          alt='RL2'
          width={60}
          height={30}
          className='object-contain'
          loading="eager"
        />
      </Link>
      </div>
    )
}