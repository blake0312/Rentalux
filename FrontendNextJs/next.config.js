/** @type {import('next').NextConfig} */
const ngrokUrl = "https://7891-2600-1700-3bac-3610-b924-8bde-a673-fa05.ngrok-free.app";
const nextConfig = {
  reactStrictMode: true,
  async rewrites() {
    return [
      {
        source: "/rental",
        destination: `${ngrokUrl}/rental`,
      },
      {
        source: "/rental/all",
        destination: `${ngrokUrl}/rental/all`,
      },
      {
        source: "/rental/:id",
        destination: `${ngrokUrl}/rental/:id`,
      },
      {
        source: "/rental/reservation",
        destination: `${ngrokUrl}/rental/reservation`,
      },
      {
        source: "/rental/reservation/:id",
        destination: `${ngrokUrl}/rental/reservation/:id`,
      },
      {
        source: "/rental/reservation/all",
        destination: "http://localhost:5001/rental/reservation/all",
      },
      {
        source: "/rental/reservation/customer/:id",
        destination: "http://localhost:5001/rental/reservation/customer/:id",
      },
    ];
  },
};

module.exports = nextConfig
