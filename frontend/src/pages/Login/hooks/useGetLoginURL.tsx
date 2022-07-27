import { getGithubURL } from '@/api/login';
import { useQuery } from 'react-query';
import { useEffect, useState } from 'react';
import { AxiosError } from 'axios';

const useGetLoginURL = () => {
	const { data, error, isError, isLoading, isSuccess, refetch } = useQuery<string, AxiosError>(
		'github-url',
		getGithubURL,
		{
			enabled: false,
		},
	);
	const [pageLoading, setPageLoading] = useState(false);

	useEffect(() => {
		if (isError) {
			throw new Error(error.message);
		}
	}, [isError]);

	useEffect(() => {
		if (isSuccess) {
			setPageLoading(true);
			window.location.href = data;
		}
	}, [isSuccess]);

	const handleLoginButtonClick = () => {
		refetch();
	};

	return { isLoading: isLoading && pageLoading, handleLoginButtonClick };
};

export default useGetLoginURL;
