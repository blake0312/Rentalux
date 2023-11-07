/** @type {import('next').NextConfig} */
const awsUrl = "http://rentalux-env.eba-tq5h4ncr.us-east-1.elasticbeanstalk.com";
const nextConfig = {
  reactStrictMode: true,
  async rewrites() {
    return [
      {
        source: "/rental",
        destination: `${awsUrl}/rental`,
      },
      {
        source: "/rental/all",
        destination: `${awsUrl}/rental/all`,
      },
      {
        source: "/rental/:id",
        destination: `${awsUrl}/rental/:id`,
      },
      {
        source: "/rental/reservation",
        destination: `${awsUrl}/rental/reservation`,
      },
      {
        source: "/rental/reservation/:id",
        destination: `${awsUrl}/rental/reservation/:id`,
      },
      {
        source: "/rental/reservation/all",
        destination: `${awsUrl}/rental/reservation/all`,
      },
      {
        source: "/rental/reservation/customer/:id",
        destination: `${awsUrl}/rental/reservation/customer/:id`,
      },
    ];
  },
};

module.exports = nextConfig
