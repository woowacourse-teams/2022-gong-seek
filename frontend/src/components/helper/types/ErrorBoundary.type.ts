import { NavigateFunction } from 'react-router-dom';

import CustomError from '@/components/helper/CustomError';

export interface ErrorBoundaryProps {
	children?: React.ReactNode;
}

export interface ErrorBoundaryState {
	error: CustomError | null;
}

export interface LogicErrorBoundaryProps extends ErrorBoundaryProps {
	showSnackBar?: (message: string) => void;
	navigate?: NavigateFunction;
}

export interface UIErrorBoundaryProps {
	serverErrorFallback: React.ReactNode;
	NotFoundErrorFallback: React.ReactNode;
}
