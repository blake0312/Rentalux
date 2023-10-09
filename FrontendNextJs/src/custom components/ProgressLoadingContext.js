import React, { createContext, useContext, useState } from 'react';

const ProgressLoadingContext = createContext();

export const ProgressLoadingProvider = ({ children }) => {
  const [progressLoading, setProgressLoading] = useState(false);

  const setProgressGlobalLoading = (progressLoading) => {
    setProgressLoading(progressLoading);
  };

  const contextValue = {
    progressLoading,
    setProgressGlobalLoading,
  };

  return (
    <ProgressLoadingContext.Provider value={contextValue}>
      {children}
    </ProgressLoadingContext.Provider>
  );
};

export const useProgressLoading = () => {
  return useContext(ProgressLoadingContext);
};