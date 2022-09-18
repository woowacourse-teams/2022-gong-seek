import { Component } from 'react';

import CustomError from '@/components/helper/CustomError';

export interface ErrorBoundaryProps {
	children?: React.ReactNode;
}

export interface ErrorBoundaryState {
	error: CustomError | null;
}

class CommonErrorBoundary<P> extends Component<P & ErrorBoundaryProps, ErrorBoundaryState> {
	constructor(props: P & ErrorBoundaryProps) {
		super(props);
		this.state = { error: null };
	}

	reset() {
		this.setState({ error: null });
	}

	static getDerivedStateFromError(error: Error) {
		return { error: error };
	}

	render() {
		return this.props.children;
	}
}

export default CommonErrorBoundary;
