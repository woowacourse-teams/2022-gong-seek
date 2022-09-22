import { AxiosError, AxiosResponse } from 'axios';
import { UseMutateFunction } from 'react-query';
import { NavigateFunction } from 'react-router-dom';

import CustomError from '@/components/helper/CustomError';
import { ErrorMessage } from '@/constants/ErrorMessage';

export interface ErrorBoundaryProps {
	children?: React.ReactNode;
}

export interface ErrorBoundaryState {
	error: CustomError | null;
}

export interface LogicErrorBoundaryProps extends ErrorBoundaryProps {
	showSnackBar?: (message: string) => void;
	navigate?: NavigateFunction;
	mutateDeleteRefreshToken?: UseMutateFunction<
		AxiosResponse<null, any>,
		AxiosError<
			{
				errorCode: keyof typeof ErrorMessage;
				message: string;
			},
			any
		>,
		void,
		unknown
	>;
}

export interface FallbackErrorBoundaryProps {
	serverErrorFallback: React.ReactNode;
	NotFoundErrorFallback: React.ReactNode;
}
