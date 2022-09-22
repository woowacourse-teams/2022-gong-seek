import { Component } from 'react';

import {
	ErrorBoundaryProps,
	ErrorBoundaryState,
} from '@/components/helper/types/ErrorBoundary.type';

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
