import styled from '@emotion/styled';

export const Container = styled.section`
	display: flex;
	flex-direction: column;

	width: 100%;
	height: fit-content;

	padding: ${({ theme }) => theme.size.SIZE_010};
	justify-content: center;
	align-items: center;
`;

export const Title = styled.div`
	width: 100%;
	margin: ${({ theme }) => theme.size.SIZE_030} 0;
`;

export const EmptyMsg = styled.div`
	font-size: ${({ theme }) => theme.size.SIZE_016};
`;
