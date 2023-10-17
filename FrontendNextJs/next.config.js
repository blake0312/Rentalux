/** @type {import('next').NextConfig} */
const ngrokUrl = "https://climbing-rapidly-roughy.ngrok-free.app";
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
        destination: `${ngrokUrl}/rental/reservation/all`,
      },
      {
        source: "/rental/reservation/customer/:id",
        destination: `${ngrokUrl}/rental/reservation/customer/:id`,
      },
    ];
  },
};

module.exports = nextConfig
