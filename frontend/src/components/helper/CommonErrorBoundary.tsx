import { PropsWithStrictChildren } from 'gongseek-types';
import { Component } from 'react';

import { ErrorBoundaryState } from '@/components/helper/types/ErrorBoundary.type';

class CommonErrorBoundary<P> extends Component<
	P & PropsWithStrictChildren<unknown>,
	ErrorBoundaryState
> {
	constructor(props: P & PropsWithStrictChildren<unknown>) {
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
