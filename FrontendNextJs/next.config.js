/** @type {import('next').NextConfig} */
const nextConfig = {
  reactStrictMode: true,
  async rewrites() {
    return [
      {
        source: "/rental",
        destination: "http://localhost:5001/rental",
      },
      {
        source: "/rental/all",
        destination: "http://localhost:5001/rental/all",
      },
      {
        source: "/rental/:id",
        destination: "http://localhost:5001/rental/:id",
      },
      {
        source: "/rental/reservation",
        destination: "http://localhost:5001/rental/reservation",
      },
      {
        source: "/rental/reservation/:id",
        destination: "http://localhost:5001/rental/reservation/:id",
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
