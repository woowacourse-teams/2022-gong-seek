import styled from '@emotion/styled';

export const Container = styled.section`
	display: flex;
	justify-content: center;
	align-items: center;
	flex-direction: column;

	width: 100%;
	height: 80vh;
`;

export const EmptyImg = styled.div`
	size: ${({ theme }) => theme.size.SIZE_040};
`;

export const EmptyDescription = styled.div`
	margin-top: ${({ theme }) => theme.size.SIZE_020};
`;
