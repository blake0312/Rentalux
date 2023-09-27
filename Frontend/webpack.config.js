const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const CopyPlugin = require("copy-webpack-plugin");
const { CleanWebpackPlugin } = require('clean-webpack-plugin');

module.exports = {
  optimization: {
    usedExports: true
  },
  entry: {
    rentalHomePage: path.resolve(__dirname, 'src', 'pages', 'rentalHomePage.js'),
    vehicleBrowsePage: path.resolve(__dirname, 'src', 'pages', 'vehicleBrowsePage.js'),
    vehicleDescriptionPage: path.resolve(__dirname, 'src', 'pages', 'vehicleDescriptionPage.js'),
    reservationManagementPage: path.resolve(__dirname, 'src', 'pages', 'reservationManagementPage.js'),
    reservationPage: path.resolve(__dirname, 'src', 'pages', 'reservationPage.js')

  },
  output: {
    path: path.resolve(__dirname, 'dist'),
    filename: '[name].js',
  },
  devServer: {
    https: false,
    port: 8080,
    open: true,
    proxy: [
      {
        context: [
          '/rental',
        ],
        target: 'http://localhost:5001'
      }
    ]
  },
  plugins: [
    new HtmlWebpackPlugin({
      template: './src/index.html',
      filename: 'index.html',
      inject: false
    }),
    new HtmlWebpackPlugin({
      template: './src/vehicle.html',
      filename: 'vehicle.html',
      inject: false
    }),
    new HtmlWebpackPlugin({
      template: './src/vehicle-details.html',
      filename: 'vehicle-details.html',
      inject: false
    }),
    new HtmlWebpackPlugin({
      template: './src/reservationManagement.html',
      filename: 'reservationManagement.html',
      inject: false
    }),
    new HtmlWebpackPlugin({
      template: './src/reservation.html',
      filename: 'reservation.html',
      inject: false
    }),
    new CopyPlugin({
      patterns: [
        {
          from: path.resolve('src/css'),
          to: path.resolve("dist/css")
        }
      ]
    }),
    new CleanWebpackPlugin()
  ]
}
