import Link from "next/link";
import Image from 'next/image'
export default function logo(){
    return (
      <div>
        <div className="logo-wrapper">
          <Link href='/' className='flex justify-center items-center'>
            <Image
              src='/RL2.svg'
              alt='RL2'
              width={50}
              height={50}
              className='object-contain'
              style={{ width: '50', height: 'auto' }}
            />
          </Link>
        </div>
      </div>
    )
}