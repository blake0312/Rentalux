import React, { createContext, useContext, useState } from 'react';
const LoadingContext = createContext();

export const LoadingProvider = ({ children }) => {
  const [loading, setLoading] = useState(false);
  const [loadingRowId, setLoadingRowId] = useState("");

  const setGlobalLoading = (isLoading, rowId) => {
    setLoading(isLoading);
    setLoadingRowId(rowId);
  };

  const contextValue = {
    loading,
    loadingRowId,
    setGlobalLoading,
  };

  return (
    <LoadingContext.Provider value={contextValue}>
      {children}
    </LoadingContext.Provider>
  );
};

export const useLoading = () => {
  return useContext(LoadingContext);
};