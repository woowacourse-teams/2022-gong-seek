import styled from '@emotion/styled';

export const Container = styled.section`
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;

	gap: ${({ theme }) => theme.size.SIZE_014};
`;

export const EmptyMsg = styled.div``;
