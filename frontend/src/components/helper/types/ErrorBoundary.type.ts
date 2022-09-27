import { NavigateFunction } from 'react-router-dom';

import CustomError from '@/components/helper/CustomError';

export interface ErrorBoundaryState {
	error: CustomError | null;
}

export interface LogicErrorBoundaryProps {
	showSnackBar?: (message: string) => void;
	navigate?: NavigateFunction;
}

export interface FallbackErrorBoundaryProps {
	serverErrorFallback: React.ReactNode;
	NotFoundErrorFallback: React.ReactNode;
}
